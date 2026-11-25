import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Bike } from '../model/bike.model';
import { Category } from '../model/category.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class BikeService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = environment.apiUrl;

  list(): Observable<Bike[]> {
    return this.http.get<Bike[]>(`${this.apiUrl}/bikes`);
  }

  get(id: number): Observable<Bike> {
    return this.http.get<Bike>(`${this.apiUrl}/bikes/${id}`);
  }

  search(name: string): Observable<Bike[]> {
    const params = new HttpParams().set('name', name);
    return this.http.get<Bike[]>(`${this.apiUrl}/bikes/search`, { params });
  }

  byCategory(categoryId: number): Observable<Bike[]> {
    return this.http.get<Bike[]>(`${this.apiUrl}/bikes/category/${categoryId}`);
  }

  categories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.apiUrl}/categories`);
  }

  create(bike: Bike): Observable<Bike> {
    return this.http.post<Bike>(`${this.apiUrl}/bikes`, bike);
  }

  update(bike: Bike): Observable<Bike> {
    return this.http.put<Bike>(`${this.apiUrl}/bikes/${bike.id}`, bike);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/bikes/${id}`);
  }
}