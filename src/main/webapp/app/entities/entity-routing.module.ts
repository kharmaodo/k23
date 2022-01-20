import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'point-of-books',
        data: { pageTitle: 'k23App.pointOfBooks.home.title' },
        loadChildren: () => import('./point-of-books/point-of-books.module').then(m => m.PointOfBooksModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
