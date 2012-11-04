require 'test_helper'

class TicketRoutesControllerTest < ActionController::TestCase
  setup do
    @ticket_route = ticket_routes(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:ticket_routes)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create ticket_route" do
    assert_difference('TicketRoute.count') do
      post :create, ticket_route: { date: @ticket_route.date }
    end

    assert_redirected_to ticket_route_path(assigns(:ticket_route))
  end

  test "should show ticket_route" do
    get :show, id: @ticket_route
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @ticket_route
    assert_response :success
  end

  test "should update ticket_route" do
    put :update, id: @ticket_route, ticket_route: { date: @ticket_route.date }
    assert_redirected_to ticket_route_path(assigns(:ticket_route))
  end

  test "should destroy ticket_route" do
    assert_difference('TicketRoute.count', -1) do
      delete :destroy, id: @ticket_route
    end

    assert_redirected_to ticket_routes_path
  end
end
