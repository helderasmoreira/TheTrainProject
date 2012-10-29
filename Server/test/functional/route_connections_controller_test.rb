require 'test_helper'

class RouteConnectionsControllerTest < ActionController::TestCase
  setup do
    @route_connection = route_connections(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:route_connections)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create route_connection" do
    assert_difference('RouteConnection.count') do
      post :create, route_connection: {  }
    end

    assert_redirected_to route_connection_path(assigns(:route_connection))
  end

  test "should show route_connection" do
    get :show, id: @route_connection
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @route_connection
    assert_response :success
  end

  test "should update route_connection" do
    put :update, id: @route_connection, route_connection: {  }
    assert_redirected_to route_connection_path(assigns(:route_connection))
  end

  test "should destroy route_connection" do
    assert_difference('RouteConnection.count', -1) do
      delete :destroy, id: @route_connection
    end

    assert_redirected_to route_connections_path
  end
end
