import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { AtendimentoFormService, AtendimentoFormGroup } from './atendimento-form.service';
import { IAtendimento } from '../atendimento.model';
import { AtendimentoService } from '../service/atendimento.service';
import { IProcedimento } from 'app/entities/procedimento/procedimento.model';
import { ProcedimentoService } from 'app/entities/procedimento/service/procedimento.service';
import { IDentista } from 'app/entities/dentista/dentista.model';
import { DentistaService } from 'app/entities/dentista/service/dentista.service';
import { IPaciente } from 'app/entities/paciente/paciente.model';
import { PacienteService } from 'app/entities/paciente/service/paciente.service';

@Component({
  selector: 'jhi-atendimento-update',
  templateUrl: './atendimento-update.component.html',
})
export class AtendimentoUpdateComponent implements OnInit {
  isSaving = false;
  atendimento: IAtendimento | null = null;

  procedimentosSharedCollection: IProcedimento[] = [];
  dentistasSharedCollection: IDentista[] = [];
  pacientesSharedCollection: IPaciente[] = [];

  editForm: AtendimentoFormGroup = this.atendimentoFormService.createAtendimentoFormGroup();

  constructor(
    protected atendimentoService: AtendimentoService,
    protected atendimentoFormService: AtendimentoFormService,
    protected procedimentoService: ProcedimentoService,
    protected dentistaService: DentistaService,
    protected pacienteService: PacienteService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProcedimento = (o1: IProcedimento | null, o2: IProcedimento | null): boolean =>
    this.procedimentoService.compareProcedimento(o1, o2);

  compareDentista = (o1: IDentista | null, o2: IDentista | null): boolean => this.dentistaService.compareDentista(o1, o2);

  comparePaciente = (o1: IPaciente | null, o2: IPaciente | null): boolean => this.pacienteService.comparePaciente(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ atendimento }) => {
      this.atendimento = atendimento;
      if (atendimento) {
        this.updateForm(atendimento);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const atendimento = this.atendimentoFormService.getAtendimento(this.editForm);
    if (atendimento.id !== null) {
      this.subscribeToSaveResponse(this.atendimentoService.update(atendimento));
    } else {
      this.subscribeToSaveResponse(this.atendimentoService.create(atendimento));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAtendimento>>): void {
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

  protected updateForm(atendimento: IAtendimento): void {
    this.atendimento = atendimento;
    this.atendimentoFormService.resetForm(this.editForm, atendimento);

    this.procedimentosSharedCollection = this.procedimentoService.addProcedimentoToCollectionIfMissing<IProcedimento>(
      this.procedimentosSharedCollection,
      atendimento.procedimento
    );
    this.dentistasSharedCollection = this.dentistaService.addDentistaToCollectionIfMissing<IDentista>(
      this.dentistasSharedCollection,
      atendimento.dentista
    );
    this.pacientesSharedCollection = this.pacienteService.addPacienteToCollectionIfMissing<IPaciente>(
      this.pacientesSharedCollection,
      atendimento.paciente
    );
  }

  protected loadRelationshipsOptions(): void {
    this.procedimentoService
      .query()
      .pipe(map((res: HttpResponse<IProcedimento[]>) => res.body ?? []))
      .pipe(
        map((procedimentos: IProcedimento[]) =>
          this.procedimentoService.addProcedimentoToCollectionIfMissing<IProcedimento>(procedimentos, this.atendimento?.procedimento)
        )
      )
      .subscribe((procedimentos: IProcedimento[]) => (this.procedimentosSharedCollection = procedimentos));

    this.dentistaService
      .query()
      .pipe(map((res: HttpResponse<IDentista[]>) => res.body ?? []))
      .pipe(
        map((dentistas: IDentista[]) =>
          this.dentistaService.addDentistaToCollectionIfMissing<IDentista>(dentistas, this.atendimento?.dentista)
        )
      )
      .subscribe((dentistas: IDentista[]) => (this.dentistasSharedCollection = dentistas));

    this.pacienteService
      .query()
      .pipe(map((res: HttpResponse<IPaciente[]>) => res.body ?? []))
      .pipe(
        map((pacientes: IPaciente[]) =>
          this.pacienteService.addPacienteToCollectionIfMissing<IPaciente>(pacientes, this.atendimento?.paciente)
        )
      )
      .subscribe((pacientes: IPaciente[]) => (this.pacientesSharedCollection = pacientes));
  }
}
