import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServeur } from '../serveur.model';

@Component({
  selector: 'jhi-serveur-detail',
  templateUrl: './serveur-detail.component.html',
})
export class ServeurDetailComponent implements OnInit {
  serveur: IServeur | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serveur }) => {
      this.serveur = serveur;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
