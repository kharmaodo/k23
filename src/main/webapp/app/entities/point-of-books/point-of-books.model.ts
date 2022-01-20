import dayjs from 'dayjs/esm';
import { StatusOfPOB } from 'app/entities/enumerations/status-of-pob.model';
import { Category } from 'app/entities/enumerations/category.model';

export interface IPointOfBooks {
  id?: string;
  name?: string;
  contentContentType?: string;
  content?: string;
  thumbnail?: string | null;
  price?: string | null;
  status?: StatusOfPOB | null;
  volume?: string | null;
  author?: string | null;
  category?: Category | null;
  createdAt?: dayjs.Dayjs | null;
  deleted?: boolean | null;
}

export class PointOfBooks implements IPointOfBooks {
  constructor(
    public id?: string,
    public name?: string,
    public contentContentType?: string,
    public content?: string,
    public thumbnail?: string | null,
    public price?: string | null,
    public status?: StatusOfPOB | null,
    public volume?: string | null,
    public author?: string | null,
    public category?: Category | null,
    public createdAt?: dayjs.Dayjs | null,
    public deleted?: boolean | null
  ) {
    this.deleted = this.deleted ?? false;
  }
}

export function getPointOfBooksIdentifier(pointOfBooks: IPointOfBooks): string | undefined {
  return pointOfBooks.id;
}
