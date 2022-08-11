import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DentistaComponent } from '../list/dentista.component';
import { DentistaDetailComponent } from '../detail/dentista-detail.component';
import { DentistaUpdateComponent } from '../update/dentista-update.component';
import { DentistaRoutingResolveService } from './dentista-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const dentistaRoute: Routes = [
  {
    path: '',
    component: DentistaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DentistaDetailComponent,
    resolve: {
      dentista: DentistaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DentistaUpdateComponent,
    resolve: {
      dentista: DentistaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DentistaUpdateComponent,
    resolve: {
      dentista: DentistaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dentistaRoute)],
  exports: [RouterModule],
})
export class DentistaRoutingModule {}
