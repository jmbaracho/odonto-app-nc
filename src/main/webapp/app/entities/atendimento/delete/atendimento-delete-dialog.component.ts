import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAtendimento } from '../atendimento.model';
import { AtendimentoService } from '../service/atendimento.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './atendimento-delete-dialog.component.html',
})
export class AtendimentoDeleteDialogComponent {
  atendimento?: IAtendimento;

  constructor(protected atendimentoService: AtendimentoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.atendimentoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
