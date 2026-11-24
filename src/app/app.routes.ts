import { Routes } from '@angular/router';
import { adminGuard } from './guards/admin.guard';
import { BikeFormComponent } from './components/bike-form/bike-form.component';
import { BikesComponent } from './components/bikes/bikes.component';
import { ForbiddenComponent } from './components/forbidden/forbidden.component';
import { LoginComponent } from './components/login/login.component';
import { SearchComponent } from './components/search/search.component';

export const routes: Routes = [
	{ path: '', pathMatch: 'full', redirectTo: 'login' },
	{ path: 'bikes', component: BikesComponent },
	{ path: 'bikes/new', component: BikeFormComponent, canActivate: [adminGuard] },
	{ path: 'bikes/:id/edit', component: BikeFormComponent, canActivate: [adminGuard] },
	{ path: 'search', component: SearchComponent },
	{ path: 'login', component: LoginComponent },
	{ path: 'forbidden', component: ForbiddenComponent },
	{ path: '**', redirectTo: 'login' },
];
