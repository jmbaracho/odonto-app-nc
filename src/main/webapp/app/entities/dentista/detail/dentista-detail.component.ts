import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDentista } from '../dentista.model';

@Component({
  selector: 'jhi-dentista-detail',
  templateUrl: './dentista-detail.component.html',
})
export class DentistaDetailComponent implements OnInit {
  dentista: IDentista | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dentista }) => {
      this.dentista = dentista;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
