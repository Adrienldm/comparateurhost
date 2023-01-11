import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ServeurComponent } from '../list/serveur.component';
import { ServeurDetailComponent } from '../detail/serveur-detail.component';
import { ServeurUpdateComponent } from '../update/serveur-update.component';
import { ServeurRoutingResolveService } from './serveur-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const serveurRoute: Routes = [
  {
    path: '',
    component: ServeurComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ServeurDetailComponent,
    resolve: {
      serveur: ServeurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ServeurUpdateComponent,
    resolve: {
      serveur: ServeurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ServeurUpdateComponent,
    resolve: {
      serveur: ServeurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(serveurRoute)],
  exports: [RouterModule],
})
export class ServeurRoutingModule {}
