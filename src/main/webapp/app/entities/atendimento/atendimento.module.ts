import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AtendimentoComponent } from './list/atendimento.component';
import { AtendimentoDetailComponent } from './detail/atendimento-detail.component';
import { AtendimentoUpdateComponent } from './update/atendimento-update.component';
import { AtendimentoDeleteDialogComponent } from './delete/atendimento-delete-dialog.component';
import { AtendimentoRoutingModule } from './route/atendimento-routing.module';

@NgModule({
  imports: [SharedModule, AtendimentoRoutingModule],
  declarations: [AtendimentoComponent, AtendimentoDetailComponent, AtendimentoUpdateComponent, AtendimentoDeleteDialogComponent],
})
export class AtendimentoModule {}
