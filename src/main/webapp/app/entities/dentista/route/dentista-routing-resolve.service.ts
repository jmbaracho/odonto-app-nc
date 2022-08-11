import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDentista } from '../dentista.model';
import { DentistaService } from '../service/dentista.service';

@Injectable({ providedIn: 'root' })
export class DentistaRoutingResolveService implements Resolve<IDentista | null> {
  constructor(protected service: DentistaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDentista | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dentista: HttpResponse<IDentista>) => {
          if (dentista.body) {
            return of(dentista.body);
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
