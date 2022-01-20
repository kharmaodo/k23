import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PointOfBooksComponent } from '../list/point-of-books.component';
import { PointOfBooksDetailComponent } from '../detail/point-of-books-detail.component';
import { PointOfBooksUpdateComponent } from '../update/point-of-books-update.component';
import { PointOfBooksRoutingResolveService } from './point-of-books-routing-resolve.service';

const pointOfBooksRoute: Routes = [
  {
    path: '',
    component: PointOfBooksComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PointOfBooksDetailComponent,
    resolve: {
      pointOfBooks: PointOfBooksRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PointOfBooksUpdateComponent,
    resolve: {
      pointOfBooks: PointOfBooksRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PointOfBooksUpdateComponent,
    resolve: {
      pointOfBooks: PointOfBooksRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pointOfBooksRoute)],
  exports: [RouterModule],
})
export class PointOfBooksRoutingModule {}
