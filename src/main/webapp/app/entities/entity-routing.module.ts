import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'dentista',
        data: { pageTitle: 'Dentistas' },
        loadChildren: () => import('./dentista/dentista.module').then(m => m.DentistaModule),
      },
      {
        path: 'paciente',
        data: { pageTitle: 'Pacientes' },
        loadChildren: () => import('./paciente/paciente.module').then(m => m.PacienteModule),
      },
      {
        path: 'procedimento',
        data: { pageTitle: 'Procedimentos' },
        loadChildren: () => import('./procedimento/procedimento.module').then(m => m.ProcedimentoModule),
      },
      {
        path: 'atendimento',
        data: { pageTitle: 'Atendimentos' },
        loadChildren: () => import('./atendimento/atendimento.module').then(m => m.AtendimentoModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
