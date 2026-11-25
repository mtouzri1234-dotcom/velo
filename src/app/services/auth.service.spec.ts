import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { AuthService } from './auth.service';

describe('AuthService', () => {
  let auth: AuthService;
  let httpTesting: HttpTestingController;

  beforeEach(() => {
    localStorage.clear();
    TestBed.configureTestingModule({
      providers: [AuthService, provideRouter([]), provideHttpClient(), provideHttpClientTesting()],
    });
    auth = TestBed.inject(AuthService);
    httpTesting = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpTesting.verify());

  it('signs in the administrator and grants admin access', () => {
    auth.signIn('admin', '123').subscribe();

    const request = httpTesting.expectOne((req) => req.url.endsWith('/api/auth/login'));
    expect(request.request.body).toEqual({ username: 'admin', password: '123' });
    request.flush({ username: 'admin', roles: ['ADMIN'] });

    expect(auth.isLoggedIn()).toBe(true);
    expect(auth.isAdmin()).toBe(true);
    expect(localStorage.getItem('bike-user')).toBe('admin');
  });

  it('signs in Youssef without admin access', () => {
    auth.signIn('youssef', '123').subscribe();

    const request = httpTesting.expectOne((req) => req.url.endsWith('/api/auth/login'));
    request.flush({ username: 'youssef', roles: ['USER'] });

    expect(auth.isLoggedIn()).toBe(true);
    expect(auth.isAdmin()).toBe(false);
  });

  it('rejects invalid credentials', () => {
    let rejected = false;
    auth.signIn('admin', 'wrong').subscribe({ error: () => (rejected = true) });

    const request = httpTesting.expectOne((req) => req.url.endsWith('/api/auth/login'));
    request.flush({ message: 'Invalid' }, { status: 401, statusText: 'Unauthorized' });

    expect(rejected).toBe(true);
    expect(auth.isLoggedIn()).toBe(false);
  });
});
