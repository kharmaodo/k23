import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { StatusOfPOB } from 'app/entities/enumerations/status-of-pob.model';
import { Category } from 'app/entities/enumerations/category.model';
import { IPointOfBooks, PointOfBooks } from '../point-of-books.model';

import { PointOfBooksService } from './point-of-books.service';

describe('PointOfBooks Service', () => {
  let service: PointOfBooksService;
  let httpMock: HttpTestingController;
  let elemDefault: IPointOfBooks;
  let expectedResult: IPointOfBooks | IPointOfBooks[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PointOfBooksService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 'AAAAAAA',
      name: 'AAAAAAA',
      contentContentType: 'image/png',
      content: 'AAAAAAA',
      thumbnail: 'AAAAAAA',
      price: 'AAAAAAA',
      status: StatusOfPOB.COLLECTION,
      volume: 'AAAAAAA',
      author: 'AAAAAAA',
      category: Category.FIQH,
      createdAt: currentDate,
      deleted: false,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          createdAt: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a PointOfBooks', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
          createdAt: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdAt: currentDate,
        },
        returnedFromService
      );

      service.create(new PointOfBooks()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PointOfBooks', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          name: 'BBBBBB',
          content: 'BBBBBB',
          thumbnail: 'BBBBBB',
          price: 'BBBBBB',
          status: 'BBBBBB',
          volume: 'BBBBBB',
          author: 'BBBBBB',
          category: 'BBBBBB',
          createdAt: currentDate.format(DATE_TIME_FORMAT),
          deleted: true,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdAt: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PointOfBooks', () => {
      const patchObject = Object.assign(
        {
          thumbnail: 'BBBBBB',
          volume: 'BBBBBB',
          author: 'BBBBBB',
          category: 'BBBBBB',
          createdAt: currentDate.format(DATE_TIME_FORMAT),
        },
        new PointOfBooks()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          createdAt: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PointOfBooks', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          name: 'BBBBBB',
          content: 'BBBBBB',
          thumbnail: 'BBBBBB',
          price: 'BBBBBB',
          status: 'BBBBBB',
          volume: 'BBBBBB',
          author: 'BBBBBB',
          category: 'BBBBBB',
          createdAt: currentDate.format(DATE_TIME_FORMAT),
          deleted: true,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdAt: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a PointOfBooks', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPointOfBooksToCollectionIfMissing', () => {
      it('should add a PointOfBooks to an empty array', () => {
        const pointOfBooks: IPointOfBooks = { id: 'ABC' };
        expectedResult = service.addPointOfBooksToCollectionIfMissing([], pointOfBooks);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pointOfBooks);
      });

      it('should not add a PointOfBooks to an array that contains it', () => {
        const pointOfBooks: IPointOfBooks = { id: 'ABC' };
        const pointOfBooksCollection: IPointOfBooks[] = [
          {
            ...pointOfBooks,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addPointOfBooksToCollectionIfMissing(pointOfBooksCollection, pointOfBooks);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PointOfBooks to an array that doesn't contain it", () => {
        const pointOfBooks: IPointOfBooks = { id: 'ABC' };
        const pointOfBooksCollection: IPointOfBooks[] = [{ id: 'CBA' }];
        expectedResult = service.addPointOfBooksToCollectionIfMissing(pointOfBooksCollection, pointOfBooks);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pointOfBooks);
      });

      it('should add only unique PointOfBooks to an array', () => {
        const pointOfBooksArray: IPointOfBooks[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: '31f06866-9665-4dad-824d-74dcc7b75757' }];
        const pointOfBooksCollection: IPointOfBooks[] = [{ id: 'ABC' }];
        expectedResult = service.addPointOfBooksToCollectionIfMissing(pointOfBooksCollection, ...pointOfBooksArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pointOfBooks: IPointOfBooks = { id: 'ABC' };
        const pointOfBooks2: IPointOfBooks = { id: 'CBA' };
        expectedResult = service.addPointOfBooksToCollectionIfMissing([], pointOfBooks, pointOfBooks2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pointOfBooks);
        expect(expectedResult).toContain(pointOfBooks2);
      });

      it('should accept null and undefined values', () => {
        const pointOfBooks: IPointOfBooks = { id: 'ABC' };
        expectedResult = service.addPointOfBooksToCollectionIfMissing([], null, pointOfBooks, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pointOfBooks);
      });

      it('should return initial array if no PointOfBooks is added', () => {
        const pointOfBooksCollection: IPointOfBooks[] = [{ id: 'ABC' }];
        expectedResult = service.addPointOfBooksToCollectionIfMissing(pointOfBooksCollection, undefined, null);
        expect(expectedResult).toEqual(pointOfBooksCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
