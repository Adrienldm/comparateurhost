import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ServeurComponent } from './list/serveur.component';
import { ServeurDetailComponent } from './detail/serveur-detail.component';
import { ServeurUpdateComponent } from './update/serveur-update.component';
import { ServeurDeleteDialogComponent } from './delete/serveur-delete-dialog.component';
import { ServeurRoutingModule } from './route/serveur-routing.module';

@NgModule({
  imports: [SharedModule, ServeurRoutingModule],
  declarations: [ServeurComponent, ServeurDetailComponent, ServeurUpdateComponent, ServeurDeleteDialogComponent],
})
export class ServeurModule {}
