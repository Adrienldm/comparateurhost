import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ServeurFormService, ServeurFormGroup } from './serveur-form.service';
import { IServeur } from '../serveur.model';
import { ServeurService } from '../service/serveur.service';

@Component({
  selector: 'jhi-serveur-update',
  templateUrl: './serveur-update.component.html',
})
export class ServeurUpdateComponent implements OnInit {
  isSaving = false;
  serveur: IServeur | null = null;

  editForm: ServeurFormGroup = this.serveurFormService.createServeurFormGroup();

  constructor(
    protected serveurService: ServeurService,
    protected serveurFormService: ServeurFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serveur }) => {
      this.serveur = serveur;
      if (serveur) {
        this.updateForm(serveur);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const serveur = this.serveurFormService.getServeur(this.editForm);
    if (serveur.id !== null) {
      this.subscribeToSaveResponse(this.serveurService.update(serveur));
    } else {
      this.subscribeToSaveResponse(this.serveurService.create(serveur));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServeur>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(serveur: IServeur): void {
    this.serveur = serveur;
    this.serveurFormService.resetForm(this.editForm, serveur);
  }
}
