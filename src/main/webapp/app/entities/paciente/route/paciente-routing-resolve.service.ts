import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPaciente } from '../paciente.model';
import { PacienteService } from '../service/paciente.service';

@Injectable({ providedIn: 'root' })
export class PacienteRoutingResolveService implements Resolve<IPaciente | null> {
  constructor(protected service: PacienteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaciente | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((paciente: HttpResponse<IPaciente>) => {
          if (paciente.body) {
            return of(paciente.body);
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
