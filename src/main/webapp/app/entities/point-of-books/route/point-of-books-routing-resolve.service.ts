import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPointOfBooks, PointOfBooks } from '../point-of-books.model';
import { PointOfBooksService } from '../service/point-of-books.service';

@Injectable({ providedIn: 'root' })
export class PointOfBooksRoutingResolveService implements Resolve<IPointOfBooks> {
  constructor(protected service: PointOfBooksService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPointOfBooks> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pointOfBooks: HttpResponse<PointOfBooks>) => {
          if (pointOfBooks.body) {
            return of(pointOfBooks.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PointOfBooks());
  }
}
