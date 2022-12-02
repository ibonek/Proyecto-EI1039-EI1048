import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LocationFromComponent } from './location-from.component';

describe('LocationFromComponent', () => {
  let component: LocationFromComponent;
  let fixture: ComponentFixture<LocationFromComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LocationFromComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LocationFromComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
