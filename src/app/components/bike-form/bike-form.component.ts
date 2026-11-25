import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Bike } from '../../model/bike.model';
import { Category } from '../../model/category.model';
import { BikeService } from '../../services/bike.service';

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './bike-form.component.html',
})
export class BikeFormComponent implements OnInit {
  private readonly api = inject(BikeService);
  private readonly changeDetector = inject(ChangeDetectorRef);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  readonly bikeId = Number(this.route.snapshot.paramMap.get('id')) || null;
  readonly isEditing = this.bikeId !== null;
  categories: Category[] = [];
  selectedCategoryId = 0;
  error = '';
  saving = false;
  bike: Bike = {
    id: null,
    name: '',
    brand: '',
    frameSize: '',
    bicycleType: '',
    price: 0,
    stock: 0,
    electric: false,
    category: { id: 0, name: '', description: '' },
  };

  ngOnInit(): void {
    this.api.categories().subscribe({
      next: (categories) => {
        this.categories = categories;
        this.changeDetector.markForCheck();
      },
      error: () => {
        this.error = 'Impossible de charger les catégories.';
        this.changeDetector.markForCheck();
      },
    });
    if (this.bikeId !== null) {
      this.api.get(this.bikeId).subscribe({
        next: (bike) => {
          this.bike = bike;
          this.selectedCategoryId = bike.category.id;
          this.changeDetector.markForCheck();
        },
        error: () => {
          this.error = 'Ce vélo est introuvable.';
          this.changeDetector.markForCheck();
        },
      });
    }
  }

  save(): void {
    const category = this.categories.find((item) => item.id === Number(this.selectedCategoryId));
    if (!category) {
      this.error = 'Sélectionnez une catégorie.';
      return;
    }
    this.bike.category = category;
    this.saving = true;
    const request = this.isEditing ? this.api.update(this.bike) : this.api.create(this.bike);
    request.subscribe({
      next: () => void this.router.navigate(['/bikes']),
      error: () => {
        this.error = 'Enregistrement impossible. Vérifiez les champs et réessayez.';
        this.saving = false;
        this.changeDetector.markForCheck();
      },
    });
  }
}