import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAtendimento } from '../atendimento.model';

@Component({
  selector: 'jhi-atendimento-detail',
  templateUrl: './atendimento-detail.component.html',
})
export class AtendimentoDetailComponent implements OnInit {
  atendimento: IAtendimento | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ atendimento }) => {
      this.atendimento = atendimento;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
