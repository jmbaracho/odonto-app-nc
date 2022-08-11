import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProcedimento } from '../procedimento.model';
import { ProcedimentoService } from '../service/procedimento.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './procedimento-delete-dialog.component.html',
})
export class ProcedimentoDeleteDialogComponent {
  procedimento?: IProcedimento;

  constructor(protected procedimentoService: ProcedimentoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.procedimentoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
