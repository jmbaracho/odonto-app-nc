import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAtendimento } from '../atendimento.model';
import { AtendimentoService } from '../service/atendimento.service';

@Injectable({ providedIn: 'root' })
export class AtendimentoRoutingResolveService implements Resolve<IAtendimento | null> {
  constructor(protected service: AtendimentoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAtendimento | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((atendimento: HttpResponse<IAtendimento>) => {
          if (atendimento.body) {
            return of(atendimento.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
