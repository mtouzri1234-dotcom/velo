import { Category } from './category.model';

export interface Bike {
  id: number | null;
  name: string;
  brand: string;
  frameSize: string;
  bicycleType: string;
  price: number;
  stock: number;
  electric: boolean;
  category: Category;
}