import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProcedimentoFormService } from './procedimento-form.service';
import { ProcedimentoService } from '../service/procedimento.service';
import { IProcedimento } from '../procedimento.model';

import { ProcedimentoUpdateComponent } from './procedimento-update.component';

describe('Procedimento Management Update Component', () => {
  let comp: ProcedimentoUpdateComponent;
  let fixture: ComponentFixture<ProcedimentoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let procedimentoFormService: ProcedimentoFormService;
  let procedimentoService: ProcedimentoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProcedimentoUpdateComponent],
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
      .overrideTemplate(ProcedimentoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProcedimentoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    procedimentoFormService = TestBed.inject(ProcedimentoFormService);
    procedimentoService = TestBed.inject(ProcedimentoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const procedimento: IProcedimento = { id: 456 };

      activatedRoute.data = of({ procedimento });
      comp.ngOnInit();

      expect(comp.procedimento).toEqual(procedimento);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProcedimento>>();
      const procedimento = { id: 123 };
      jest.spyOn(procedimentoFormService, 'getProcedimento').mockReturnValue(procedimento);
      jest.spyOn(procedimentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ procedimento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: procedimento }));
      saveSubject.complete();

      // THEN
      expect(procedimentoFormService.getProcedimento).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(procedimentoService.update).toHaveBeenCalledWith(expect.objectContaining(procedimento));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProcedimento>>();
      const procedimento = { id: 123 };
      jest.spyOn(procedimentoFormService, 'getProcedimento').mockReturnValue({ id: null });
      jest.spyOn(procedimentoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ procedimento: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: procedimento }));
      saveSubject.complete();

      // THEN
      expect(procedimentoFormService.getProcedimento).toHaveBeenCalled();
      expect(procedimentoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProcedimento>>();
      const procedimento = { id: 123 };
      jest.spyOn(procedimentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ procedimento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(procedimentoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
