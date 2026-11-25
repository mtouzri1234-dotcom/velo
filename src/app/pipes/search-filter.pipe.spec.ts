import { Bike } from '../model/bike.model';
import { SearchFilterPipe } from './search-filter.pipe';

describe('SearchFilterPipe', () => {
  const bikes: Bike[] = [
    {
      id: 1,
      name: 'Trail 7',
      brand: 'Trek',
      frameSize: 'L',
      bicycleType: 'VTT',
      price: 1299,
      stock: 5,
      electric: false,
      category: { id: 2, name: 'VTT', description: '' },
    },
  ];
  const pipe = new SearchFilterPipe();

  it('filters by name and brand without case sensitivity', () => {
    expect(pipe.transform(bikes, 'TREK')).toEqual(bikes);
    expect(pipe.transform(bikes, 'route')).toEqual([]);
  });

  it('returns the original list for an empty query', () => {
    expect(pipe.transform(bikes, '')).toEqual(bikes);
  });
});