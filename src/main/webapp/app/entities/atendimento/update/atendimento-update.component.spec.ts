import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AtendimentoFormService } from './atendimento-form.service';
import { AtendimentoService } from '../service/atendimento.service';
import { IAtendimento } from '../atendimento.model';
import { IProcedimento } from 'app/entities/procedimento/procedimento.model';
import { ProcedimentoService } from 'app/entities/procedimento/service/procedimento.service';
import { IDentista } from 'app/entities/dentista/dentista.model';
import { DentistaService } from 'app/entities/dentista/service/dentista.service';
import { IPaciente } from 'app/entities/paciente/paciente.model';
import { PacienteService } from 'app/entities/paciente/service/paciente.service';

import { AtendimentoUpdateComponent } from './atendimento-update.component';

describe('Atendimento Management Update Component', () => {
  let comp: AtendimentoUpdateComponent;
  let fixture: ComponentFixture<AtendimentoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let atendimentoFormService: AtendimentoFormService;
  let atendimentoService: AtendimentoService;
  let procedimentoService: ProcedimentoService;
  let dentistaService: DentistaService;
  let pacienteService: PacienteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AtendimentoUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AtendimentoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AtendimentoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    atendimentoFormService = TestBed.inject(AtendimentoFormService);
    atendimentoService = TestBed.inject(AtendimentoService);
    procedimentoService = TestBed.inject(ProcedimentoService);
    dentistaService = TestBed.inject(DentistaService);
    pacienteService = TestBed.inject(PacienteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Procedimento query and add missing value', () => {
      const atendimento: IAtendimento = { id: 456 };
      const procedimentos: IProcedimento[] = [{ id: 43526 }];
      atendimento.procedimentos = procedimentos;

      const procedimentoCollection: IProcedimento[] = [{ id: 15564 }];
      jest.spyOn(procedimentoService, 'query').mockReturnValue(of(new HttpResponse({ body: procedimentoCollection })));
      const additionalProcedimentos = [...procedimentos];
      const expectedCollection: IProcedimento[] = [...additionalProcedimentos, ...procedimentoCollection];
      jest.spyOn(procedimentoService, 'addProcedimentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ atendimento });
      comp.ngOnInit();

      expect(procedimentoService.query).toHaveBeenCalled();
      expect(procedimentoService.addProcedimentoToCollectionIfMissing).toHaveBeenCalledWith(
        procedimentoCollection,
        ...additionalProcedimentos.map(expect.objectContaining)
      );
      expect(comp.procedimentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Dentista query and add missing value', () => {
      const atendimento: IAtendimento = { id: 456 };
      const dentista: IDentista = { id: 85154 };
      atendimento.dentista = dentista;

      const dentistaCollection: IDentista[] = [{ id: 85613 }];
      jest.spyOn(dentistaService, 'query').mockReturnValue(of(new HttpResponse({ body: dentistaCollection })));
      const additionalDentistas = [dentista];
      const expectedCollection: IDentista[] = [...additionalDentistas, ...dentistaCollection];
      jest.spyOn(dentistaService, 'addDentistaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ atendimento });
      comp.ngOnInit();

      expect(dentistaService.query).toHaveBeenCalled();
      expect(dentistaService.addDentistaToCollectionIfMissing).toHaveBeenCalledWith(
        dentistaCollection,
        ...additionalDentistas.map(expect.objectContaining)
      );
      expect(comp.dentistasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Paciente query and add missing value', () => {
      const atendimento: IAtendimento = { id: 456 };
      const paciente: IPaciente = { id: 88979 };
      atendimento.paciente = paciente;

      const pacienteCollection: IPaciente[] = [{ id: 53091 }];
      jest.spyOn(pacienteService, 'query').mockReturnValue(of(new HttpResponse({ body: pacienteCollection })));
      const additionalPacientes = [paciente];
      const expectedCollection: IPaciente[] = [...additionalPacientes, ...pacienteCollection];
      jest.spyOn(pacienteService, 'addPacienteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ atendimento });
      comp.ngOnInit();

      expect(pacienteService.query).toHaveBeenCalled();
      expect(pacienteService.addPacienteToCollectionIfMissing).toHaveBeenCalledWith(
        pacienteCollection,
        ...additionalPacientes.map(expect.objectContaining)
      );
      expect(comp.pacientesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const atendimento: IAtendimento = { id: 456 };
      const procedimento: IProcedimento = { id: 73672 };
      atendimento.procedimentos = [procedimento];
      const dentista: IDentista = { id: 84278 };
      atendimento.dentista = dentista;
      const paciente: IPaciente = { id: 61656 };
      atendimento.paciente = paciente;

      activatedRoute.data = of({ atendimento });
      comp.ngOnInit();

      expect(comp.procedimentosSharedCollection).toContain(procedimento);
      expect(comp.dentistasSharedCollection).toContain(dentista);
      expect(comp.pacientesSharedCollection).toContain(paciente);
      expect(comp.atendimento).toEqual(atendimento);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAtendimento>>();
      const atendimento = { id: 123 };
      jest.spyOn(atendimentoFormService, 'getAtendimento').mockReturnValue(atendimento);
      jest.spyOn(atendimentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ atendimento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: atendimento }));
      saveSubject.complete();

      // THEN
      expect(atendimentoFormService.getAtendimento).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(atendimentoService.update).toHaveBeenCalledWith(expect.objectContaining(atendimento));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAtendimento>>();
      const atendimento = { id: 123 };
      jest.spyOn(atendimentoFormService, 'getAtendimento').mockReturnValue({ id: null });
      jest.spyOn(atendimentoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ atendimento: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: atendimento }));
      saveSubject.complete();

      // THEN
      expect(atendimentoFormService.getAtendimento).toHaveBeenCalled();
      expect(atendimentoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAtendimento>>();
      const atendimento = { id: 123 };
      jest.spyOn(atendimentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ atendimento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(atendimentoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareProcedimento', () => {
      it('Should forward to procedimentoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(procedimentoService, 'compareProcedimento');
        comp.compareProcedimento(entity, entity2);
        expect(procedimentoService.compareProcedimento).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDentista', () => {
      it('Should forward to dentistaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(dentistaService, 'compareDentista');
        comp.compareDentista(entity, entity2);
        expect(dentistaService.compareDentista).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePaciente', () => {
      it('Should forward to pacienteService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(pacienteService, 'comparePaciente');
        comp.comparePaciente(entity, entity2);
        expect(pacienteService.comparePaciente).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
