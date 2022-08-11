import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DentistaComponent } from './list/dentista.component';
import { DentistaDetailComponent } from './detail/dentista-detail.component';
import { DentistaUpdateComponent } from './update/dentista-update.component';
import { DentistaDeleteDialogComponent } from './delete/dentista-delete-dialog.component';
import { DentistaRoutingModule } from './route/dentista-routing.module';

@NgModule({
  imports: [SharedModule, DentistaRoutingModule],
  declarations: [DentistaComponent, DentistaDetailComponent, DentistaUpdateComponent, DentistaDeleteDialogComponent],
})
export class DentistaModule {}
