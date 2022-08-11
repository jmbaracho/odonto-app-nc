import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDentista, NewDentista } from '../dentista.model';

export type PartialUpdateDentista = Partial<IDentista> & Pick<IDentista, 'id'>;

type RestOf<T extends IDentista | NewDentista> = Omit<T, 'dataNascimento'> & {
  dataNascimento?: string | null;
};

export type RestDentista = RestOf<IDentista>;

export type NewRestDentista = RestOf<NewDentista>;

export type PartialUpdateRestDentista = RestOf<PartialUpdateDentista>;

export type EntityResponseType = HttpResponse<IDentista>;
export type EntityArrayResponseType = HttpResponse<IDentista[]>;

@Injectable({ providedIn: 'root' })
export class DentistaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/dentistas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(dentista: NewDentista): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dentista);
    return this.http
      .post<RestDentista>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(dentista: IDentista): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dentista);
    return this.http
      .put<RestDentista>(`${this.resourceUrl}/${this.getDentistaIdentifier(dentista)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(dentista: PartialUpdateDentista): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dentista);
    return this.http
      .patch<RestDentista>(`${this.resourceUrl}/${this.getDentistaIdentifier(dentista)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDentista>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDentista[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDentistaIdentifier(dentista: Pick<IDentista, 'id'>): number {
    return dentista.id;
  }

  compareDentista(o1: Pick<IDentista, 'id'> | null, o2: Pick<IDentista, 'id'> | null): boolean {
    return o1 && o2 ? this.getDentistaIdentifier(o1) === this.getDentistaIdentifier(o2) : o1 === o2;
  }

  addDentistaToCollectionIfMissing<Type extends Pick<IDentista, 'id'>>(
    dentistaCollection: Type[],
    ...dentistasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const dentistas: Type[] = dentistasToCheck.filter(isPresent);
    if (dentistas.length > 0) {
      const dentistaCollectionIdentifiers = dentistaCollection.map(dentistaItem => this.getDentistaIdentifier(dentistaItem)!);
      const dentistasToAdd = dentistas.filter(dentistaItem => {
        const dentistaIdentifier = this.getDentistaIdentifier(dentistaItem);
        if (dentistaCollectionIdentifiers.includes(dentistaIdentifier)) {
          return false;
        }
        dentistaCollectionIdentifiers.push(dentistaIdentifier);
        return true;
      });
      return [...dentistasToAdd, ...dentistaCollection];
    }
    return dentistaCollection;
  }

  protected convertDateFromClient<T extends IDentista | NewDentista | PartialUpdateDentista>(dentista: T): RestOf<T> {
    return {
      ...dentista,
      dataNascimento: dentista.dataNascimento?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restDentista: RestDentista): IDentista {
    return {
      ...restDentista,
      dataNascimento: restDentista.dataNascimento ? dayjs(restDentista.dataNascimento) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDentista>): HttpResponse<IDentista> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDentista[]>): HttpResponse<IDentista[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
