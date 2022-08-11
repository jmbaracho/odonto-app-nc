import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AtendimentoDetailComponent } from './atendimento-detail.component';

describe('Atendimento Management Detail Component', () => {
  let comp: AtendimentoDetailComponent;
  let fixture: ComponentFixture<AtendimentoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AtendimentoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ atendimento: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AtendimentoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AtendimentoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load atendimento on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.atendimento).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
