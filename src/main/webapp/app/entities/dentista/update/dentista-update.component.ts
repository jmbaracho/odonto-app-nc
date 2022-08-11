import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { DentistaFormService, DentistaFormGroup } from './dentista-form.service';
import { IDentista } from '../dentista.model';
import { DentistaService } from '../service/dentista.service';

@Component({
  selector: 'jhi-dentista-update',
  templateUrl: './dentista-update.component.html',
})
export class DentistaUpdateComponent implements OnInit {
  isSaving = false;
  dentista: IDentista | null = null;

  editForm: DentistaFormGroup = this.dentistaFormService.createDentistaFormGroup();

  constructor(
    protected dentistaService: DentistaService,
    protected dentistaFormService: DentistaFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dentista }) => {
      this.dentista = dentista;
      if (dentista) {
        this.updateForm(dentista);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dentista = this.dentistaFormService.getDentista(this.editForm);
    if (dentista.id !== null) {
      this.subscribeToSaveResponse(this.dentistaService.update(dentista));
    } else {
      this.subscribeToSaveResponse(this.dentistaService.create(dentista));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDentista>>): void {
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

  protected updateForm(dentista: IDentista): void {
    this.dentista = dentista;
    this.dentistaFormService.resetForm(this.editForm, dentista);
  }
}
