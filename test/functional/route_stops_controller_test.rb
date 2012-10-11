require 'test_helper'

class RouteStopsControllerTest < ActionController::TestCase
  setup do
    @route_stop = route_stops(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:route_stops)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create route_stop" do
    assert_difference('RouteStop.count') do
      post :create, route_stop: { delay: @route_stop.delay, stop_order: @route_stop.stop_order }
    end

    assert_redirected_to route_stop_path(assigns(:route_stop))
  end

  test "should show route_stop" do
    get :show, id: @route_stop
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @route_stop
    assert_response :success
  end

  test "should update route_stop" do
    put :update, id: @route_stop, route_stop: { delay: @route_stop.delay, stop_order: @route_stop.stop_order }
    assert_redirected_to route_stop_path(assigns(:route_stop))
  end

  test "should destroy route_stop" do
    assert_difference('RouteStop.count', -1) do
      delete :destroy, id: @route_stop
    end

    assert_redirected_to route_stops_path
  end
end
