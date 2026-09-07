import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: 'fade', pathMatch: 'full' },
  {
    path: 'fade',
    loadComponent: () => import('./features/fade/fade-list/fade-list.component').then(m => m.FadeListComponent)
  },
  {
    path: 'destilleringer',
    loadComponent: () => import('./features/destillering/destillering-list/destillering-list.component').then(m => m.DestilleringListComponent)
  },
  {
    path: 'lagerstyring',
    loadComponent: () => import('./features/lagerstyring/lagerstyring.component').then(m => m.LagerstyringComponent)
  },
  {
    path: 'whisky',
    loadComponent: () => import('./features/whisky/whisky-list/whisky-list.component').then(m => m.WhiskyListComponent)
  }
];
