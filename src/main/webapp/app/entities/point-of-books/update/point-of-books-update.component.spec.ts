import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PointOfBooksService } from '../service/point-of-books.service';
import { IPointOfBooks, PointOfBooks } from '../point-of-books.model';

import { PointOfBooksUpdateComponent } from './point-of-books-update.component';

describe('PointOfBooks Management Update Component', () => {
  let comp: PointOfBooksUpdateComponent;
  let fixture: ComponentFixture<PointOfBooksUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pointOfBooksService: PointOfBooksService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PointOfBooksUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PointOfBooksUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PointOfBooksUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pointOfBooksService = TestBed.inject(PointOfBooksService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const pointOfBooks: IPointOfBooks = { id: 'CBA' };

      activatedRoute.data = of({ pointOfBooks });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(pointOfBooks));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PointOfBooks>>();
      const pointOfBooks = { id: 'ABC' };
      jest.spyOn(pointOfBooksService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pointOfBooks });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pointOfBooks }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(pointOfBooksService.update).toHaveBeenCalledWith(pointOfBooks);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PointOfBooks>>();
      const pointOfBooks = new PointOfBooks();
      jest.spyOn(pointOfBooksService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pointOfBooks });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pointOfBooks }));
      saveSubject.complete();

      // THEN
      expect(pointOfBooksService.create).toHaveBeenCalledWith(pointOfBooks);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PointOfBooks>>();
      const pointOfBooks = { id: 'ABC' };
      jest.spyOn(pointOfBooksService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pointOfBooks });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pointOfBooksService.update).toHaveBeenCalledWith(pointOfBooks);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
