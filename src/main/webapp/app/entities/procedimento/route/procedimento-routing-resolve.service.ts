import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProcedimento } from '../procedimento.model';
import { ProcedimentoService } from '../service/procedimento.service';

@Injectable({ providedIn: 'root' })
export class ProcedimentoRoutingResolveService implements Resolve<IProcedimento | null> {
  constructor(protected service: ProcedimentoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProcedimento | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((procedimento: HttpResponse<IProcedimento>) => {
          if (procedimento.body) {
            return of(procedimento.body);
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
