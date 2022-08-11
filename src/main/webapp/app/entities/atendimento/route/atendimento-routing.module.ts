import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AtendimentoComponent } from '../list/atendimento.component';
import { AtendimentoDetailComponent } from '../detail/atendimento-detail.component';
import { AtendimentoUpdateComponent } from '../update/atendimento-update.component';
import { AtendimentoRoutingResolveService } from './atendimento-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const atendimentoRoute: Routes = [
  {
    path: '',
    component: AtendimentoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AtendimentoDetailComponent,
    resolve: {
      atendimento: AtendimentoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AtendimentoUpdateComponent,
    resolve: {
      atendimento: AtendimentoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AtendimentoUpdateComponent,
    resolve: {
      atendimento: AtendimentoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(atendimentoRoute)],
  exports: [RouterModule],
})
export class AtendimentoRoutingModule {}
