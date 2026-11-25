import { CommonModule } from '@angular/common';
import { Component, inject, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Bike } from '../../model/bike.model';
import { Category } from '../../model/category.model';
import { SearchFilterPipe } from '../../pipes/search-filter.pipe';
import { BikeService } from '../../services/bike.service';

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule, SearchFilterPipe],
  templateUrl: './search.component.html',
})
export class SearchComponent implements OnInit {
  private readonly api = inject(BikeService);
  bikes = signal<Bike[]>([]);
  categories = signal<Category[]>([]);
  searchTerm = '';
  selectedCategoryId = 0;
  error = signal('');

  ngOnInit(): void {
    this.loadBikes();
    this.api.categories().subscribe({
      next: (categories) => this.categories.set(categories),
      error: () => this.error.set('Impossible de charger les catégories.'),
    });
  }

  searchOnServer(): void {
    const term = this.searchTerm.trim();
    if (!term) {
      this.loadBikes();
      return;
    }
    this.api.search(term).subscribe({
      next: (bikes) => this.bikes.set(bikes),
      error: () => this.error.set('La recherche serveur a échoué.'),
    });
  }

  onCategoryChange(): void {
    if (!this.selectedCategoryId) {
      this.loadBikes();
      return;
    }
    this.api.byCategory(this.selectedCategoryId).subscribe({
      next: (bikes) => this.bikes.set(bikes),
      error: () => this.error.set('Le filtre par catégorie a échoué.'),
    });
  }

  private loadBikes(): void {
    this.api.list().subscribe({
      next: (bikes) => this.bikes.set(bikes),
      error: () => this.error.set('Impossible de joindre le serveur. Vérifiez que l’API Spring Boot est démarrée.'),
    });
  }
}