import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DentistaDetailComponent } from './dentista-detail.component';

describe('Dentista Management Detail Component', () => {
  let comp: DentistaDetailComponent;
  let fixture: ComponentFixture<DentistaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DentistaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ dentista: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DentistaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DentistaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load dentista on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.dentista).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
