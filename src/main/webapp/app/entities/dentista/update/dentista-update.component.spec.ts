import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DentistaFormService } from './dentista-form.service';
import { DentistaService } from '../service/dentista.service';
import { IDentista } from '../dentista.model';

import { DentistaUpdateComponent } from './dentista-update.component';

describe('Dentista Management Update Component', () => {
  let comp: DentistaUpdateComponent;
  let fixture: ComponentFixture<DentistaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dentistaFormService: DentistaFormService;
  let dentistaService: DentistaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DentistaUpdateComponent],
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
      .overrideTemplate(DentistaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DentistaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    dentistaFormService = TestBed.inject(DentistaFormService);
    dentistaService = TestBed.inject(DentistaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const dentista: IDentista = { id: 456 };

      activatedRoute.data = of({ dentista });
      comp.ngOnInit();

      expect(comp.dentista).toEqual(dentista);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDentista>>();
      const dentista = { id: 123 };
      jest.spyOn(dentistaFormService, 'getDentista').mockReturnValue(dentista);
      jest.spyOn(dentistaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dentista });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dentista }));
      saveSubject.complete();

      // THEN
      expect(dentistaFormService.getDentista).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(dentistaService.update).toHaveBeenCalledWith(expect.objectContaining(dentista));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDentista>>();
      const dentista = { id: 123 };
      jest.spyOn(dentistaFormService, 'getDentista').mockReturnValue({ id: null });
      jest.spyOn(dentistaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dentista: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dentista }));
      saveSubject.complete();

      // THEN
      expect(dentistaFormService.getDentista).toHaveBeenCalled();
      expect(dentistaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDentista>>();
      const dentista = { id: 123 };
      jest.spyOn(dentistaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dentista });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(dentistaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
