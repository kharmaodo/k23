import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPointOfBooks, getPointOfBooksIdentifier } from '../point-of-books.model';

export type EntityResponseType = HttpResponse<IPointOfBooks>;
export type EntityArrayResponseType = HttpResponse<IPointOfBooks[]>;

@Injectable({ providedIn: 'root' })
export class PointOfBooksService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/point-of-books');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pointOfBooks: IPointOfBooks): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pointOfBooks);
    return this.http
      .post<IPointOfBooks>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pointOfBooks: IPointOfBooks): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pointOfBooks);
    return this.http
      .put<IPointOfBooks>(`${this.resourceUrl}/${getPointOfBooksIdentifier(pointOfBooks) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(pointOfBooks: IPointOfBooks): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pointOfBooks);
    return this.http
      .patch<IPointOfBooks>(`${this.resourceUrl}/${getPointOfBooksIdentifier(pointOfBooks) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IPointOfBooks>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPointOfBooks[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPointOfBooksToCollectionIfMissing(
    pointOfBooksCollection: IPointOfBooks[],
    ...pointOfBooksToCheck: (IPointOfBooks | null | undefined)[]
  ): IPointOfBooks[] {
    const pointOfBooks: IPointOfBooks[] = pointOfBooksToCheck.filter(isPresent);
    if (pointOfBooks.length > 0) {
      const pointOfBooksCollectionIdentifiers = pointOfBooksCollection.map(
        pointOfBooksItem => getPointOfBooksIdentifier(pointOfBooksItem)!
      );
      const pointOfBooksToAdd = pointOfBooks.filter(pointOfBooksItem => {
        const pointOfBooksIdentifier = getPointOfBooksIdentifier(pointOfBooksItem);
        if (pointOfBooksIdentifier == null || pointOfBooksCollectionIdentifiers.includes(pointOfBooksIdentifier)) {
          return false;
        }
        pointOfBooksCollectionIdentifiers.push(pointOfBooksIdentifier);
        return true;
      });
      return [...pointOfBooksToAdd, ...pointOfBooksCollection];
    }
    return pointOfBooksCollection;
  }

  protected convertDateFromClient(pointOfBooks: IPointOfBooks): IPointOfBooks {
    return Object.assign({}, pointOfBooks, {
      createdAt: pointOfBooks.createdAt?.isValid() ? pointOfBooks.createdAt.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt ? dayjs(res.body.createdAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((pointOfBooks: IPointOfBooks) => {
        pointOfBooks.createdAt = pointOfBooks.createdAt ? dayjs(pointOfBooks.createdAt) : undefined;
      });
    }
    return res;
  }
}
