import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ProcedimentoFormService, ProcedimentoFormGroup } from './procedimento-form.service';
import { IProcedimento } from '../procedimento.model';
import { ProcedimentoService } from '../service/procedimento.service';

@Component({
  selector: 'jhi-procedimento-update',
  templateUrl: './procedimento-update.component.html',
})
export class ProcedimentoUpdateComponent implements OnInit {
  isSaving = false;
  procedimento: IProcedimento | null = null;

  editForm: ProcedimentoFormGroup = this.procedimentoFormService.createProcedimentoFormGroup();

  constructor(
    protected procedimentoService: ProcedimentoService,
    protected procedimentoFormService: ProcedimentoFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ procedimento }) => {
      this.procedimento = procedimento;
      if (procedimento) {
        this.updateForm(procedimento);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const procedimento = this.procedimentoFormService.getProcedimento(this.editForm);
    if (procedimento.id !== null) {
      this.subscribeToSaveResponse(this.procedimentoService.update(procedimento));
    } else {
      this.subscribeToSaveResponse(this.procedimentoService.create(procedimento));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProcedimento>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(procedimento: IProcedimento): void {
    this.procedimento = procedimento;
    this.procedimentoFormService.resetForm(this.editForm, procedimento);
  }
}
