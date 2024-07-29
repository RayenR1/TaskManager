import { TestBed } from '@angular/core/testing';

import { AddTacheService } from './add-tache.service';

describe('AddTacheService', () => {
  let service: AddTacheService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AddTacheService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
