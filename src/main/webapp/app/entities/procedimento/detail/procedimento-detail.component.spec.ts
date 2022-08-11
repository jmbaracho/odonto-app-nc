import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProcedimentoDetailComponent } from './procedimento-detail.component';

describe('Procedimento Management Detail Component', () => {
  let comp: ProcedimentoDetailComponent;
  let fixture: ComponentFixture<ProcedimentoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProcedimentoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ procedimento: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ProcedimentoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProcedimentoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load procedimento on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.procedimento).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
