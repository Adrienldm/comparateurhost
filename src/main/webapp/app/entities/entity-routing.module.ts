import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'serveur',
        data: { pageTitle: 'archiCloudApp.serveur.home.title' },
        loadChildren: () => import('./serveur/serveur.module').then(m => m.ServeurModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
