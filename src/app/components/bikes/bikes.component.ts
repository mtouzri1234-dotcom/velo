import { CommonModule } from '@angular/common';
import { Component, inject, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Bike } from '../../model/bike.model';
import { AuthService } from '../../services/auth.service';
import { BikeService } from '../../services/bike.service';

@Component({
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './bikes.component.html',
})
export class BikesComponent implements OnInit {
  private readonly api = inject(BikeService);
  readonly auth = inject(AuthService);
  bikes = signal<Bike[]>([]);
  error = signal('');
  loading = signal(true);

  ngOnInit(): void {
    this.loadBikes();
  }

  loadBikes(): void {
    this.loading.set(true);
    this.api.list().subscribe({
      next: (bikes) => {
        this.bikes.set(bikes);
        this.loading.set(false);
        this.error.set('');
      },
      error: () => {
        this.error.set('Impossible de joindre le serveur. Vérifiez que l’API Spring Boot est démarrée.');
        this.loading.set(false);
      },
    });
  }

  remove(bike: Bike): void {
    if (bike.id === null || !confirm(`Supprimer « ${bike.name} » du parc ?`)) return;
    this.api.delete(bike.id).subscribe({
      next: () => this.loadBikes(),
      error: () => this.error.set('La suppression a échoué. Réessayez.'),
    });
  }

  get electricCount(): number {
    return this.bikes().filter((bike) => bike.electric).length;
  }

  get totalStock(): number {
    return this.bikes().reduce((total, bike) => total + bike.stock, 0);
  }
}