import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProcedimento, NewProcedimento } from '../procedimento.model';

export type PartialUpdateProcedimento = Partial<IProcedimento> & Pick<IProcedimento, 'id'>;

export type EntityResponseType = HttpResponse<IProcedimento>;
export type EntityArrayResponseType = HttpResponse<IProcedimento[]>;

@Injectable({ providedIn: 'root' })
export class ProcedimentoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/procedimentos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(procedimento: NewProcedimento): Observable<EntityResponseType> {
    return this.http.post<IProcedimento>(this.resourceUrl, procedimento, { observe: 'response' });
  }

  update(procedimento: IProcedimento): Observable<EntityResponseType> {
    return this.http.put<IProcedimento>(`${this.resourceUrl}/${this.getProcedimentoIdentifier(procedimento)}`, procedimento, {
      observe: 'response',
    });
  }

  partialUpdate(procedimento: PartialUpdateProcedimento): Observable<EntityResponseType> {
    return this.http.patch<IProcedimento>(`${this.resourceUrl}/${this.getProcedimentoIdentifier(procedimento)}`, procedimento, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProcedimento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProcedimento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProcedimentoIdentifier(procedimento: Pick<IProcedimento, 'id'>): number {
    return procedimento.id;
  }

  compareProcedimento(o1: Pick<IProcedimento, 'id'> | null, o2: Pick<IProcedimento, 'id'> | null): boolean {
    return o1 && o2 ? this.getProcedimentoIdentifier(o1) === this.getProcedimentoIdentifier(o2) : o1 === o2;
  }

  addProcedimentoToCollectionIfMissing<Type extends Pick<IProcedimento, 'id'>>(
    procedimentoCollection: Type[],
    ...procedimentosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const procedimentos: Type[] = procedimentosToCheck.filter(isPresent);
    if (procedimentos.length > 0) {
      const procedimentoCollectionIdentifiers = procedimentoCollection.map(
        procedimentoItem => this.getProcedimentoIdentifier(procedimentoItem)!
      );
      const procedimentosToAdd = procedimentos.filter(procedimentoItem => {
        const procedimentoIdentifier = this.getProcedimentoIdentifier(procedimentoItem);
        if (procedimentoCollectionIdentifiers.includes(procedimentoIdentifier)) {
          return false;
        }
        procedimentoCollectionIdentifiers.push(procedimentoIdentifier);
        return true;
      });
      return [...procedimentosToAdd, ...procedimentoCollection];
    }
    return procedimentoCollection;
  }
}
