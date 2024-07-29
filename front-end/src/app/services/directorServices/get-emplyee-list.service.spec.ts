import { TestBed } from '@angular/core/testing';

import { GetEmplyeeListService } from './get-emplyee-list.service';

describe('GetEmplyeeListService', () => {
  let service: GetEmplyeeListService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GetEmplyeeListService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
