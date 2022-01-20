import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPointOfBooks, PointOfBooks } from '../point-of-books.model';
import { PointOfBooksService } from '../service/point-of-books.service';

import { PointOfBooksRoutingResolveService } from './point-of-books-routing-resolve.service';

describe('PointOfBooks routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PointOfBooksRoutingResolveService;
  let service: PointOfBooksService;
  let resultPointOfBooks: IPointOfBooks | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(PointOfBooksRoutingResolveService);
    service = TestBed.inject(PointOfBooksService);
    resultPointOfBooks = undefined;
  });

  describe('resolve', () => {
    it('should return IPointOfBooks returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPointOfBooks = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultPointOfBooks).toEqual({ id: 'ABC' });
    });

    it('should return new IPointOfBooks if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPointOfBooks = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPointOfBooks).toEqual(new PointOfBooks());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PointOfBooks })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPointOfBooks = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultPointOfBooks).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
