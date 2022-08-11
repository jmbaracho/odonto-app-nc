import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { AtendimentoService } from '../service/atendimento.service';

import { AtendimentoComponent } from './atendimento.component';

describe('Atendimento Management Component', () => {
  let comp: AtendimentoComponent;
  let fixture: ComponentFixture<AtendimentoComponent>;
  let service: AtendimentoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'atendimento', component: AtendimentoComponent }]), HttpClientTestingModule],
      declarations: [AtendimentoComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(AtendimentoComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AtendimentoComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AtendimentoService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.atendimentos?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to atendimentoService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getAtendimentoIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getAtendimentoIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
