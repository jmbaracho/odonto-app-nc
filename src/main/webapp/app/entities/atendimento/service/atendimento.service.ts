import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAtendimento, NewAtendimento } from '../atendimento.model';

export type PartialUpdateAtendimento = Partial<IAtendimento> & Pick<IAtendimento, 'id'>;

type RestOf<T extends IAtendimento | NewAtendimento> = Omit<T, 'dataAtendimento'> & {
  dataAtendimento?: string | null;
};

export type RestAtendimento = RestOf<IAtendimento>;

export type NewRestAtendimento = RestOf<NewAtendimento>;

export type PartialUpdateRestAtendimento = RestOf<PartialUpdateAtendimento>;

export type EntityResponseType = HttpResponse<IAtendimento>;
export type EntityArrayResponseType = HttpResponse<IAtendimento[]>;

@Injectable({ providedIn: 'root' })
export class AtendimentoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/atendimentos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(atendimento: NewAtendimento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(atendimento);
    return this.http
      .post<RestAtendimento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(atendimento: IAtendimento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(atendimento);
    return this.http
      .put<RestAtendimento>(`${this.resourceUrl}/${this.getAtendimentoIdentifier(atendimento)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(atendimento: PartialUpdateAtendimento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(atendimento);
    return this.http
      .patch<RestAtendimento>(`${this.resourceUrl}/${this.getAtendimentoIdentifier(atendimento)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAtendimento>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAtendimento[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAtendimentoIdentifier(atendimento: Pick<IAtendimento, 'id'>): number {
    return atendimento.id;
  }

  compareAtendimento(o1: Pick<IAtendimento, 'id'> | null, o2: Pick<IAtendimento, 'id'> | null): boolean {
    return o1 && o2 ? this.getAtendimentoIdentifier(o1) === this.getAtendimentoIdentifier(o2) : o1 === o2;
  }

  addAtendimentoToCollectionIfMissing<Type extends Pick<IAtendimento, 'id'>>(
    atendimentoCollection: Type[],
    ...atendimentosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const atendimentos: Type[] = atendimentosToCheck.filter(isPresent);
    if (atendimentos.length > 0) {
      const atendimentoCollectionIdentifiers = atendimentoCollection.map(
        atendimentoItem => this.getAtendimentoIdentifier(atendimentoItem)!
      );
      const atendimentosToAdd = atendimentos.filter(atendimentoItem => {
        const atendimentoIdentifier = this.getAtendimentoIdentifier(atendimentoItem);
        if (atendimentoCollectionIdentifiers.includes(atendimentoIdentifier)) {
          return false;
        }
        atendimentoCollectionIdentifiers.push(atendimentoIdentifier);
        return true;
      });
      return [...atendimentosToAdd, ...atendimentoCollection];
    }
    return atendimentoCollection;
  }

  protected convertDateFromClient<T extends IAtendimento | NewAtendimento | PartialUpdateAtendimento>(atendimento: T): RestOf<T> {
    return {
      ...atendimento,
      dataAtendimento: atendimento.dataAtendimento?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restAtendimento: RestAtendimento): IAtendimento {
    return {
      ...restAtendimento,
      dataAtendimento: restAtendimento.dataAtendimento ? dayjs(restAtendimento.dataAtendimento) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAtendimento>): HttpResponse<IAtendimento> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAtendimento[]>): HttpResponse<IAtendimento[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
