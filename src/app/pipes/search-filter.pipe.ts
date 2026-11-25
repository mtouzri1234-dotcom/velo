import { Pipe, PipeTransform } from '@angular/core';
import { Bike } from '../model/bike.model';

@Pipe({ name: 'searchFilter', standalone: true })
export class SearchFilterPipe implements PipeTransform {
  transform(bikes: Bike[] | null, query: string): Bike[] {
    if (!bikes) return [];
    const normalizedQuery = query.trim().toLocaleLowerCase('fr');
    if (!normalizedQuery) return bikes;

    return bikes.filter((bike) =>
      [bike.name, bike.brand, bike.bicycleType, bike.frameSize, bike.category.name]
        .join(' ')
        .toLocaleLowerCase('fr')
        .includes(normalizedQuery),
    );
  }
}