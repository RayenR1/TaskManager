import { TestBed } from '@angular/core/testing';

import { GetEmplyeeTaskService } from './get-emplyee-task.service';

describe('GetEmplyeeTaskService', () => {
  let service: GetEmplyeeTaskService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GetEmplyeeTaskService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
